# ChatRoom

This project was created using the [Ktor Project Generator](https://start.ktor.io).

Here are some useful links to get you started:

- [Ktor Documentation](https://ktor.io/docs/home.html)
- [Ktor GitHub page](https://github.com/ktorio/ktor)
- The [Ktor Slack chat](https://app.slack.com/client/T09229ZC6/C0A974TJ9). You'll need
  to [request an invite](https://surveys.jetbrains.com/s3/kotlin-slack-sign-up) to join.

## Features

Here's a list of features included in this project:

| Name                                               | Description                                                 |
|----------------------------------------------------|-------------------------------------------------------------|
| [Routing](https://start.ktor.io/p/routing-default) | Allows to define structured routes and associated handlers. |

## Building & Running

To build or run the project, use one of the following tasks:

| Task                          | Description                                                          |
|-------------------------------|----------------------------------------------------------------------|
| `./gradlew test`              | Run the tests                                                        |
| `./gradlew build`             | Build everything                                                     |
| `buildFatJar`                 | Build an executable JAR of the server with all dependencies included |
| `buildImage`                  | Build the docker image to use with the fat JAR                       |
| `publishImageToLocalRegistry` | Publish the docker image locally                                     |
| `run`                         | Run the server                                                       |
| `runDocker`                   | Run using the local docker image                                     |

If the server starts successfully, you'll see the following output:

```
2024-12-04 14:32:45.584 [main] INFO  Application - Application started in 0.303 seconds.
2024-12-04 14:32:45.682 [main] INFO  Application - Responding at http://0.0.0.0:8080
```

### Run EngineMain on Application.kt
### Run on ngrok
### add ngrok config add-authtoken XXXXXXXXXXXXXXXXXXXXX
### ngrok http 8080
### get the URL from ngrok -> https://....ngrok-free.app

---

## 如何用 ngrok 讓聊天室外網可連

1. 啟動 Ktor 伺服器
   - 在 IDE 執行 Application.kt，或在終端機輸入：
     ```
     ./gradlew run
     ```

2. 註冊 ngrok 並安裝
   - 前往 [ngrok 官網](https://ngrok.com/) 註冊帳號並下載 ngrok。
   - 解壓縮後，將 ngrok.exe 放到你想要的位置。

3. 設定 ngrok authtoken（只需做一次）
   - 在終端機輸入：
     ```
     ngrok config add-authtoken 你的-ngrok-authtoken
     ```

4. 啟動 ngrok 轉發本機 8080 port
   - 在終端機輸入：
     ```
     ngrok http 8080
     ```

5. 取得 ngrok 公開網址
   - 終端機會顯示類似 `https://xxxx-xxxx-xxxx.ngrok-free.app` 的網址。

6. 分享網址給其他人
   - 其他人用這個網址就能連到你的聊天室。
