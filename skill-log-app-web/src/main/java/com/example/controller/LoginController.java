package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * ログイン画面を表示するだけのシンプルなコントローラです。
 *
 * <p>フォームログインの認証処理は Spring Security のフィルタが担当し、
 * このクラスの責務は「GET /login でテンプレートを返すこと」のみに限定します。
 * <p>画面側で {@code th:action="@{/login}"} を利用することで、/login POST に送信されるフォームデータが
 * そのまま Spring Security に渡される点が背景です。
 * <p>注意: このクラスでセッション操作やエラーメッセージを操作しないことが保守性向上につながります。
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
