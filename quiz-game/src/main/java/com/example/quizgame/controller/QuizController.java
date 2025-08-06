package com.example.quizgame.controller;

import com.example.quizgame.model.Question;
import com.example.quizgame.service.QuizService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/quiz")
    public String getQuizPage(Model model) {
        List<Question> questions = quizService.getAllQuestions();
        model.addAttribute("questions", questions);
        return "quiz";
    }

    @PostMapping("/submit")
    public String submitQuiz(@RequestParam Map<String, String> allParams, Model model) {
        List<Question> questions = quizService.getAllQuestions();
        int score = 0;
        int total = questions.size();

        StringBuilder result = new StringBuilder();

        for (Question q : questions) {
            String userAnswer = allParams.get("q" + q.getId());
            if (q.getCorrectAnswer().equalsIgnoreCase(userAnswer)) {
                score++;
            } else {
                result.append("<p><b>Question:</b> ").append(q.getQuestionText()).append("<br>");
                result.append("<b>Your Answer:</b> ").append(userAnswer).append("<br>");
                result.append("<b>Correct Answer:</b> ").append(q.getCorrectAnswer()).append("</p>");
            }
        }

        model.addAttribute("score", score);
        model.addAttribute("total", total);
        model.addAttribute("result", result.toString());
        return "result";
    }
}
