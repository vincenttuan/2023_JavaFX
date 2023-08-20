# 2023_JavaFX
2023_JavaFX

# Python Window 安裝
https://www.python.org/downloads/

# Window 設定 pip 與 python 執行檔
C:\Users\你的名字\AppData\Local\Programs\Python\Python311\Scripts<br>
C:\Users\你的名字\AppData\Local\Programs\Python\Python311<br>
設定到環境變數<br>

# 安裝 BestFourPoint
https://twstock.readthedocs.io/zh_TW/latest/prepare.html

pip install --user twstock<br>
pip install lxml

# 範例
import twstock<br>
twstock.__update_codes()<br>
stock = twstock.Stock('2330')<br>
print(bfp.best_four_point_to_buy())  # False<br>
print(bfp.best_four_point_to_sell()) # 量縮價跌, 三日均價由上往下, 三日均價小於六日均價<br>
print(bfp.best_four_point())  # (False, '量縮價跌, 三日均價由上往下, 三日均價小於六日均價')<br>
