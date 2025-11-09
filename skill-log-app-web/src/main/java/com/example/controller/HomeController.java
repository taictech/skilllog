package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * ルートパス（/）から既存の SkillLog 画面へ誘導するためのリダイレクト専用コントローラです。
 *
 * <p>フォームログイン成功時のデフォルト遷移先を {@code /} に固定しつつ、
 * 実際の画面は {@code /skilllog-web} にまとめているため、このクラスがゲート役を担います。
 * <p>ここでレスポンス本文を持たないことで、ログイン導線の責務とコンテンツ表示の責務を分離しています。
 * <p>注意: 今後トップページを実装する際は SecurityConfig の {@code defaultSuccessUrl} と整合を取る必要があります。
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/skilllog-web";
    }
}
