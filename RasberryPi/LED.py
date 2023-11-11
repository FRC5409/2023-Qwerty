import ntcore
import time
import math

from rpi_ws281x import PixelStrip, ws

# ssh pi@chargerspi.local
# frc5409
# scp C:\Users\aslsz\OneDrive\Documents\Github\python-nt-test\LED.py pi@chargerspi.local:
# sudo python LED.py

LED_CHANNEL = 0
LED_COUNT = 60              # How many LEDs to light.
LED_FREQ_HZ = 800000        # Frequency of the LED signal.  Should be 800khz or 400khz.
LED_DMA_NUM = 10            # DMA channel to use, can be 0-14.
LED_GPIO = 18               # GPIO connected to the LED signal line.  Must support PWM!
LED_BRIGHTNESS = 155

REFRESH_TIME = 0.05

BLINK_SPEED = 0.25          # in REFRESH_TIME intervals

SIN_COUNT = 8
SIN_SPEED = 0.5

SIN_HS = 0.05
SIN_VS = 30.0

RAINBOW_CYCLE_SPEED = 0.5   # negative speed to reverse

LED_STRIP = ws.SK6812W_STRIP

led = ws.new_ws2811_t()

# Initialize all channels to off
for channum in range(2):
    channel = ws.ws2811_channel_get(led, channum)
    ws.ws2811_channel_t_count_set(channel, 0)
    ws.ws2811_channel_t_gpionum_set(channel, 0)
    ws.ws2811_channel_t_invert_set(channel, 0)
    ws.ws2811_channel_t_brightness_set(channel, 0)

channel = ws.ws2811_channel_get(led, LED_CHANNEL)

ws.ws2811_channel_t_count_set(channel, LED_COUNT)
ws.ws2811_channel_t_gpionum_set(channel, LED_GPIO)
ws.ws2811_channel_t_invert_set(channel, 0)
ws.ws2811_channel_t_brightness_set(channel, LED_BRIGHTNESS)
ws.ws2811_channel_t_strip_type_set(channel, LED_STRIP)

ws.ws2811_t_freq_set(led, LED_FREQ_HZ)
ws.ws2811_t_dmanum_set(led, LED_DMA_NUM)

ws.ws2811_init(led)


inst = ntcore.NetworkTableInstance.getDefault()
inst.startClient4("10.54.9.2")
inst.setServer("10.54.9.2")

table = inst.getTable("Shuffleboard").getSubTable("LED_COMMUNICATION_TAB")

LEDstate = table.getIntegerTopic("STATE").subscribe(3)
LEDPrimeColor = table.getIntegerArrayTopic("PRIMARY_COLOR").subscribe([242, 242, 5])
LEDSecondColor = table.getIntegerArrayTopic("SECONDARY_COLOR").subscribe([0, 0, 0])

MultiVariate = table.getDoubleTopic("MULTIVARIATE").subscribe(0)

lastState = -1
state = -1

LEDTimer = 0
animationTime = 0

def Color(r, g, b):
    return int('{:02x}{:02x}{:02x}'.format(r, g, b), 16)

primaryColor = Color(242, 242, 5)
secondaryColor = Color(0, 0, 0)

def setLEDColor(color):
    # pixels.fill(hex((color[0], color[1], color[2])))
    for i in range(0, LED_COUNT):
        ws.ws2811_led_set(channel, i, color)

def setLEDColorAt(index, color):
    ws.ws2811_led_set(channel, index, color)

def blinkLEDs():
    global LEDTimer, animationTime, primaryColor, secondaryColor
    # Simple blink LED function
    speed = getMultivariate(BLINK_SPEED)

    if LEDTimer <= speed:
        setLEDColor(primaryColor)
    elif LEDTimer <= speed * 2:
        setLEDColor(secondaryColor)
    else:
        LEDTimer = 0.0
        animationTime = 0
        return

    LEDTimer += REFRESH_TIME

def sinWave():
    global LEDTimer, primaryColor, secondaryColor
    LEDTimer += SIN_SPEED

    for i in range(0, LED_COUNT):
        if (((i + LEDTimer) % (SIN_COUNT * 2)) <= SIN_COUNT):
            setLEDColorAt(i, primaryColor)
        else:
            setLEDColorAt(i, secondaryColor)

def sinFlow():
    global LEDTimer, animationTime, primaryColor, secondaryColor
    LEDTimer += 1

    animationTime = math.sin(LEDTimer * SIN_HS) * SIN_VS

    # Makes the function smoother
    if (abs(animationTime) >= SIN_VS - 0.5):
        LEDTimer += 2
    
    animationTime = math.floor(animationTime)

    for i in range(-SIN_COUNT, LED_COUNT + SIN_COUNT):
        if (((i + animationTime) % (SIN_COUNT * 2)) <= SIN_COUNT):
            if (i >= 0 & i < LED_COUNT):
                setLEDColorAt(i, primaryColor)
        else:
            if (i >= 0 & i < LED_COUNT):
                setLEDColorAt(i, secondaryColor)

def rainbowCycle():
    global LEDTimer
    LEDTimer -= RAINBOW_CYCLE_SPEED

    rainbow_colors_rgb = [
        Color(255, 0, 0),      # Red
        Color(255, 69, 0),     # Orange
        Color(255, 255, 0),    # Yellow
        Color(0, 255, 0),      # Green
        Color(0, 0, 255),      # Blue
        Color(138, 43, 226)    # Violet
    ]

    for i in range(LED_COUNT):
            setLEDColorAt(i, rainbow_colors_rgb[int(LEDTimer + i) % len(rainbow_colors_rgb)])

def rainbowBlink():
    global LEDTimer
    LEDTimer -= RAINBOW_CYCLE_SPEED

    rainbow_colors_rgb = [
        Color(255, 0, 0),      # Red
        Color(255, 69, 0),     # Orange
        Color(255, 255, 0),    # Yellow
        Color(0, 255, 0),      # Green
        Color(0, 0, 255),      # Blue
        Color(138, 43, 226)    # Violet
    ]

    for i in range(LED_COUNT):
        setLEDColor(rainbow_colors_rgb[(int(LEDTimer)) % len(rainbow_colors_rgb)])
    

def getMultivariate(multivariateContester):
    mul = 0
    if MultiVariate.get(0) == -1:
        mul = multivariateContester
    else:
        mul = MultiVariate.get(0)

    return mul

while True:
    if inst.isConnected():
        lastState = state
        state = LEDstate.get()

        nt = LEDPrimeColor.get()

        primaryColor = Color(nt[0], nt[1], nt[2])

        nt = LEDSecondColor.get()

        secondaryColor = Color(nt[0], nt[1], nt[2])

        if lastState != state:
            LEDTimer = 0.0

        if state == 0:
            setLEDColor(primaryColor)
        elif state == 1:
            blinkLEDs()
        elif state == 2:
            sinWave()
        elif state == 3:
            sinFlow()
        elif state == 4:
            rainbowCycle()
        elif state == 5:
            rainbowBlink()
    else:
        rainbowCycle()

    ws.ws2811_render(led)
    time.sleep(REFRESH_TIME)