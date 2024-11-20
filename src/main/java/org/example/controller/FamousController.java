package org.example.controller;

import org.example.controller.model.FamousSayingModel;
import org.example.service.FamousService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * 명언 관련 컨트롤러
 *
 * @author shjung
 * @since 2024. 11. 19.
 */
public class FamousController {

    private final FamousService famousService = new FamousService();;

    private static List<FamousSayingModel> famousSayingModels = new ArrayList<>();

    private static Scanner scanner = new Scanner(System.in);

    /**
     *
     * 명언 등록 함수
     *
     * @param id 명언 등록 ID
     *
     * @return 등록된 ID보다 한 단계 높은 숫자
     *
     * @author shjung
     * @since 2024. 11. 20.
     */
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

    /**
     *
     * 명언 목록 출력 함수
     *
     * @author shjung
     * @since 2024. 11. 20.
     */
    public void printFamousSayings(String value){
        famousSayingModels = famousService.readFamousSayings(value);
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------");
        famousSayingModels.stream().forEach(f -> {
            System.out.println(f.getId() + " / " + f.getAuthor() + " / " + f.getContents());
        });
    }

    /**
     *
     * 명언 수정 함수
     *
     * @param value 명령어
     *
     * @author shjung
     * @since 2024. 11. 20.
     */
    public void updateFamousSaying(String value){
        // 명령어에서 찾는 ID 추출
        int id = Integer.parseInt(value.replaceAll("[^0-9]", ""));

        // 찾은 ID를 통해서 수정하고자 하는 명언 검색
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

    /**
     *
     * 명언 삭제 함수
     *
     * @param value 명령어
     *
     * @author shjung
     * @since 2024. 11. 20.
     */
    public void deleteFamousSaying(String value){
        int id = Integer.parseInt(value.replaceAll("[^0-9]", ""));

        String message = famousService.deleteFamousSaying(id);
        System.out.println(message);
    }

    /**
     *
     * 마지막 ID 검색
     *
     * @return 마지막 ID
     *
     * @author shjung
     * @since 2024. 11. 20.
     */
    public int readLastId(){
        return famousService.readLastId();
    }

    /**
     *
     * data.json 빌드
     *
     * @author shjung
     * @since 2024. 11. 20.
     */
    public void buildFamousSaying(){
        String message = famousService.createDataJson(famousSayingModels);
        System.out.println(message);
    }
}
