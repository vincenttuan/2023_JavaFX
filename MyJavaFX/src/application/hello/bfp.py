# -*- coding: utf-8 -*-
import twstock
twstock.__update_codes()
stock = twstock.Stock('2330')
bfp = twstock.BestFourPoint(stock)
print(bfp.best_four_point()) # (False, '量縮價跌, 三日均價由上往下, 三日均價小於六日均價')
