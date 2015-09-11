# Paraponera

Docker Registryのv2に対応したWeb UIです。  
Docker Registryで用意されているAPIで取れる情報しか表示していません。

とりあえず作っただけなので、検索とかないです。  
画面をロードするたびにRegistry API叩くのがあれだったので、登録してあるDocker Registryに  
定期的にアクセスしてRedisに入れるようにしています。  
この時間は設定で変更できます。

課題はいっぱいあるので順次対応していきます。  
とりあえずRegistry登録しておけばコンテナイメージを確認することはできるようになったので公開します。
