# Horizonte Entregas — projeto Android (WebView + Firebase Cloud Messaging)

Este projeto empacota a página `web/index.html` em um aplicativo Android e inclui a base nativa para receber notificações push via Firebase Cloud Messaging (FCM).

## Situação importante
O ZIP recebido contém um site com autenticação/cadastro, mas o próprio README informa que ainda não implementa distribuição de corridas nem notificações push. Portanto, este projeto é uma **base Android**, não um sistema de pedidos pronto. Para que notificações reais de novos pedidos cheguem ao entregador, ainda é necessário:
1. Configurar o app Android no projeto Firebase `horizonte-entregas`, com o applicationId `com.horizonte.entregas`.
2. Baixar o `google-services.json` e colocá-lo em `android/app/google-services.json`.
3. Salvar o token FCM de cada entregador autenticado no Firestore/backend (o código deixa o ponto de integração indicado).
4. Implementar o fluxo de criação/distribuição de pedidos e um backend confiável que envie mensagens FCM. **Nunca** envie mensagens FCM usando credenciais administrativas dentro do APK ou do site.
5. Criar e testar regras do Firestore para que cada entregador só receba os pedidos autorizados.

## Gerar APK usando Termux
A compilação Android é mais confiável em um computador com Android SDK instalado. No Android/Termux, o Android SDK/Build Tools não é oficialmente suportado em todas as combinações de aparelho. Se o Gradle falhar por falta do SDK, use GitHub Actions (workflow a adicionar) ou compile em um PC.

### Preparar o projeto no Termux
Instale Termux de fonte confiável e execute:
```bash
pkg update && pkg upgrade -y
pkg install git unzip openjdk-17 -y
unzip horizonte-entregas-android.zip
cd horizonte-entregas-android/android
```
Instale/configure o Android SDK e defina `ANDROID_HOME` e `ANDROID_SDK_ROOT` apontando para o SDK. O `local.properties` deve conter:
```properties
sdk.dir=/caminho/absoluto/para/Android/Sdk
```
Coloque `google-services.json` em `app/google-services.json` antes de compilar.
Com Gradle 8.9 ou compatível com Android Gradle Plugin 8.7.3:
```bash
gradle assembleDebug
```
O APK de teste deverá ser gerado em:
`app/build/outputs/apk/debug/app-debug.apk`

## Enviar para o GitHub pelo Termux
Crie primeiro um repositório vazio no GitHub. Depois, dentro da pasta `horizonte-entregas-android`:
```bash
git init
git add .
git commit -m "Adiciona app Android Horizonte Entregas"
git branch -M main
git remote add origin https://github.com/SEU-USUARIO/horizonte-entregas-android.git
git push -u origin main
```
Troque `SEU-USUARIO` pelo seu usuário do GitHub. Se pedir senha, use um Personal Access Token (PAT), não a senha da conta. Nunca publique `google-services.json` se o projeto tiver sido configurado para conter informações que você não quer expor; revise também arquivos e segredos antes do `git push`.

## Próximo passo
Depois de criar o app Android no Firebase, envie o `google-services.json` (ou diga se prefere que eu guie você pelo console). Também preciso confirmar como os pedidos são criados: pela loja dentro do site ou por outro sistema.
