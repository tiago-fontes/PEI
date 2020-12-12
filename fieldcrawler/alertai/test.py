# Test File for AlertAI module


import pandas as pd
import numpy as np
import alertai as AI
import getLast


if __name__ == '__main__':
    variable = getLast.getLast()
    #print(variable)
    ml = AI.AlertAI()
    ml.start()
    pre_process = ml.processData(variable)
    print("JA ACABEIII")



