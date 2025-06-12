# SkillLog アプリ

副業・スキル学習など、日々の取り組みを記録・管理できるWebアプリです。  
シンプルな構成で、学習進捗やスキルの可視化をサポートします。

---

## 📌 主な機能

- スキルログの新規登録
- 一覧表示（降順）
- 編集・削除
- カテゴリ別の表示（予定）

---

## 🛠 使用技術

- Java 17
- Spring Boot 3.2.x
- Thymeleaf
- H2 Database（組み込みDB）
- Maven（ビルド管理）

---

## 🚀 セットアップ方法

以下の手順でローカル環境にセットアップできます。

```bash
git clone https://github.com/your-username/skilllog-app.git
cd skilllog-app
./mvnw spring-boot:run
```

---

## 📸 スクリーンショット

### 📋 登録画面
新しいスキルログをカテゴリ・時間・日付ごとに入力して記録する画面です。
![登録画面](images/ws001_register.jpeg)

### 📝 一覧画面
登録されたスキルログを日付降順で表示する画面です。
![一覧画面](images/ws002_list.jpeg)


---

## ✅ 補足：使い方

- **ファイル名**：`README.md`
- **場所**：プロジェクトのルートディレクトリ（`src/`と同階層）に置く

---
## 🔧 今後の実装予定

- ログイン機能（Spring Security）
- Bootstrap導入によるUI改善
- タグによる絞り込み／検索

## 🔗 関連リンク
- Zenn記事：「[Spring Boot入門] スキル学習ログを記録するWebアプリを作成してみた」  
  https://zenn.dev/taictech/articles/785409c42d9f63

