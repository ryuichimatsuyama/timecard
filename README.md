# timecard
タイムカードです。

# 概要
timecardは以下の機能を実装しています。

- 新規従業員登録機能:
このアプリを利用するには新規従業員登録が必要です。
管理者が「名前」、「番号」、「パスワード」、「管理者か一般」、「休憩時間」、「時給」を従業員情報として登録します。

- Login/Logout機能:
新規従業員登録後はログインをしてこのアプリを使うことができます。

- 打刻機能:
あらかじめ上司を登録して、登録した上司を選択して出勤します。
退勤したら休憩時間を引いた労働時間と日給が自動で計算されます。

- 承認機能:
指定された上司はそのタイムカードを承認します。
編集もできます。
編集は指定された上司しかできません。

- 月給表示機能:
「月給」、「日数」、「労働時間」が表示されます。
承認されたタイムカードしか反映されません。


# 設計図

サイトマップ

![サイトマップ](https://gyazo.com/b9960d8ce0202943544d60852bfde413/raw)


データベース図
![データベース図](https://gyazo.com/04547ee6a5847f4486cf17ffa0b5b92a/raw)



# 使用方法
動画をご確認ください。

## Heroku URL
https://servlet-timecard.herokuapp.com/

## 動画
- 打刻機能

![打刻機能](https://gyazo.com/e87abd3755a15ae889266ad9d7cc4800/raw)


- ログイン/承認機能/ログアウト

![ログイン・承認機能・ログアウト](https://gyazo.com/30884fb0003cca6782fa275fa62ee5ca/raw)

# 著者
Ryuichi Matsuyama


