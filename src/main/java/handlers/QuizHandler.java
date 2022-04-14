package handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import interfaces.PageConstants;
import models.Account;
import models.MultipleChoiceQuestion;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuizHandler {

    int questionId = 0;
    int amount = 5;
    int correct = 0;
    int wrong = 0;
    String category;
    List<MultipleChoiceQuestion> questions;

    boolean isComplete = false;

    public QuizHandler(int questionId, int amount, String category) {
        this.questionId = questionId;
        this.amount = amount;
        this.category = category;
    }

    public void startQuiz() {
        addActionListeners(MemoryHandler.textAreas);

        while(questions == null) {
            questions = getQuestions();
        }

        setupQuestion();
    }

    private void setupQuestion() {
        MultipleChoiceQuestion question = questions.get(this.questionId);

        MemoryHandler.questionTextArea.setText(question.getQuestion());
        MemoryHandler.labels[0].setText("Category: " + question.getCategory().replaceAll("&quot;", "\"").replaceAll("&#039;", "'"));
        MemoryHandler.labels[1].setText("Question " + (questionId+1) + "/" + amount);

        List<String> possible_answers = question.getIncorrect_answers();
        possible_answers.add(question.getCorrect_answer());

        Collections.shuffle(possible_answers);
        JTextArea[] textAreas = MemoryHandler.textAreas;
        for(int j = 0; j < possible_answers.size(); j++) {
            textAreas[j].setText(possible_answers.get(j).replaceAll("&quot;", "\"").replaceAll("&#039;", "'"));
        }
    }

    private void addActionListeners(JTextArea[] textAreas) {
        for(JTextArea textArea : textAreas) {
            textArea.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if(e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON2 || e.getButton() == MouseEvent.BUTTON3) {
                        if(!isComplete) {
                            if (textArea.getText().equalsIgnoreCase(questions.get(questionId).getCorrect_answer())) {
                                correct++;
                            } else {
                                wrong++;
                            }

                            MemoryHandler.labels[2].setText("Correct: " + correct);
                            MemoryHandler.labels[3].setText("Wrong: " + wrong);

                            questionId++;
                            if (questionId == amount) {

                                if(correct/wrong > 0.3) {
                                    JOptionPane.showMessageDialog(MemoryHandler.quizGUI.tabbedPane, "Congratulations! You finished the quiz with " + correct + " correct out of " + amount + " possible.");
                                }
                                else {
                                    JOptionPane.showMessageDialog(MemoryHandler.quizGUI.tabbedPane, correct + " correct... you suck lol");
                                }
                                isComplete = true;

                                Account account = MemoryHandler.account;
                                account.setAnsweredCorrect(account.getAnsweredCorrect()+correct);
                                account.setAnswered(account.getAnswered()+amount);

                                MemoryHandler.quizGUI.setPage(PageConstants.MAIN_PAGE);
                            } else {
                                setupQuestion();
                            }
                        }
                    }
                }
            });
        }
    }

    private List<MultipleChoiceQuestion> getQuestions() {
        String apiPrefix = "https://opentdb.com/api.php?";
        String apiAmount = "amount=" + amount;
        String apiCategory = category == "Any category" ? "" : "&category=" + getValueFromCategory(category);
        String apiSuffix = "&type=multiple";
        URI uri = URI.create(apiPrefix + apiAmount + apiCategory + apiSuffix);

        System.out.println(uri.toString());

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String json = response.body();
        json = json.substring(29);
        json = json.substring(0, json.length() - 1);
        System.out.println(json);

        ObjectMapper objectMapper = new ObjectMapper();
        MultipleChoiceQuestion[] questions = new MultipleChoiceQuestion[amount];
        try {
            questions = objectMapper.readValue(json, MultipleChoiceQuestion[].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return Arrays.asList(questions);
    }

    private int getValueFromCategory(String category) {
        switch (category) {
            case "General knowledge":
                return 9;
            case "Books":
                return 10;
            case "Film":
                return 11;
            case "Music":
                return 12;
            case "Musicals & Theatres":
                return 13;
            case "Television":
                return 14;
            case "Video games":
                return 15;
            case "Board games":
                return 16;
            case "Science & Nature":
                return 17;
            case "Animals":
                return 27;
            case "Celebrities":
                return 26;
            case "Art":
                return 25;
            case "Politics":
                return 24;
            case "History":
                return 23;
            case "Geography":
                return 22;
            case "Sports":
                return 21;
            case "Mythology":
                return 20;
            case "Mathematics":
                return 19;
            case "Computers":
                return 18;
        }
        return 0;
    }

}
