# -*- coding: utf-8 -*-
import sys;
import twstock
#twstock.__update_codes()
stock_code = sys.argv[1]  # 從命令行參數讀取股票代碼
stock = twstock.Stock(stock_code)
bfp = twstock.BestFourPoint(stock)
print(bfp.best_four_point()) # (False, '量縮價跌, 三日均價由上往下, 三日均價小於六日均價')
