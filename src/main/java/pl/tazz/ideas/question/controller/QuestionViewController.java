package pl.tazz.ideas.question.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.tazz.ideas.category.service.CategoryService;
import pl.tazz.ideas.question.domain.model.Question;
import pl.tazz.ideas.question.service.AnswerService;
import pl.tazz.ideas.question.service.QuestionService;

import java.util.UUID;

@Controller
@RequestMapping("/questions")
public class QuestionViewController {

    private final QuestionService questionsService;
    private final AnswerService answersService;
    private final CategoryService categoryService;

    public QuestionViewController(QuestionService questionsService,
                                  AnswerService answersService,
                                  CategoryService categoryService) {
        this.questionsService = questionsService;
        this.answersService = answersService;
        this.categoryService = categoryService;
    }

    @GetMapping({"/",""})
    public String indexViev(Model model) {
        model.addAttribute("questions", questionsService.getQuestions());
        model.addAttribute("categories", categoryService.getCategories());

        return "question/index";
    }

    @GetMapping("{id}")
    public String singleViev(@PathVariable UUID id, Model model) {

        model.addAttribute( "question", questionsService.getQuestion(id));
        model.addAttribute("answers", answersService.getAnswers(id));
        model.addAttribute("categories", categoryService.getCategories());

        return "question/single";
    }

    @GetMapping("add")
    public String addViev(Model model) {
        model.addAttribute("question", new Question());

        return "question/add";
    }

    @PostMapping
    public String add(Question question){
    questionsService.createQuestion(question);

    return "redirect:/questions";
    }
}
