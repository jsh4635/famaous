package org.example.controller;

import org.example.controller.model.FamousSayingModel;
import org.example.service.FamousService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// 등록, 호출, 수정, 삭제 호출
public class FamousController {

    private final FamousService famousService = new FamousService();;

    private static List<FamousSayingModel> famousSayingModels = new ArrayList<>();

    private static Scanner scanner = new Scanner(System.in);

    public int addFamousSaying(int id){
        // 명언 데이터 입력
        System.out.print("명언: ");
        String contents = scanner.nextLine();
        System.out.print("작가: ");
        String author = scanner.nextLine();
        String message = famousService.addFamousSaying(id, contents, author);
        System.out.println(message);
        return id + 1;
    }

    public void printFamousSayings(){
        famousSayingModels = famousService.readFamousSayings();
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------");
        famousSayingModels.stream().forEach(f -> {
            System.out.println(f.getId() + " / " + f.getAuthor() + " / " + f.getContents());
        });
    }

    public void updateFamousSaying(String value){
        int id = Integer.parseInt(value.replaceAll("[^0-9]", ""));
        FamousSayingModel famousSayingModel = famousService.readFamousSaying(id);

        System.out.println("명언(기존): " + famousSayingModel.getContents());
        System.out.print("명언: ");
        String contents = scanner.nextLine();
        System.out.println("작가(기존): " + famousSayingModel.getContents());
        System.out.print("작가: ");
        String author = scanner.nextLine();

        famousSayingModel.setContents(contents);
        famousSayingModel.setAuthor(author);

        String message = famousService.updateFamousSaying(famousSayingModel);

        System.out.println(message);
    }

    public void deleteFamousSaying(String value){
        int id = Integer.parseInt(value.replaceAll("[^0-9]", ""));

        String message = famousService.deleteFamousSaying(id);
        System.out.println(message);
    }

    public int readLastId(){
        return famousService.readLastId();
    }

    public void buildFamousSaying(){
        String message = famousService.createDataJson(famousSayingModels);
        System.out.println(message);
    }
}
