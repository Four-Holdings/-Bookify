package com.example.bookify.domain.rental.controller.controller;

import com.example.bookify.domain.rental.service.dto.BookRentalResponseDto;
import com.example.bookify.domain.rental.service.service.BookRentalService;
import com.example.bookify.global.common.apiresponse.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RequestMapping("/api/rentals")
@RestController
@RequiredArgsConstructor
public class BookRentalController {

    private final BookRentalService rentalService;


    //1. 도서 대여 처리
    @PostMapping("/{bookId}")
    public ResponseEntity<ResponseDto<BookRentalResponseDto>> rentBook(
            @PathVariable Long bookId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Long userId = Long.parseLong(userDetails.getUsername());
        BookRentalResponseDto response =  rentalService.rentBook(bookId, userId);
        return ResponseEntity.ok(new ResponseDto<>("도서 대여가 완료되었습니다.", response));
    }


    //2. 도서 대여 반납
    @PatchMapping("/{rentalId}/return")
    public ResponseEntity<ResponseDto<BookRentalResponseDto>> returnBook(
            @PathVariable Long rentalId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Long userId = Long.parseLong(userDetails.getUsername());
        BookRentalResponseDto response = rentalService.returnBook(rentalId, userId);
        return ResponseEntity.ok(new ResponseDto<>("도서 반납이 완료되었습니다.", response));
    }




    //3.도서 상태 조회
    @GetMapping("/status")
    public void checkRentalStatus(@RequestParam Long bookId){
        ResponseEntity<String> response = new ResponseEntity<>("success",HttpStatus.OK);

    }



    //4.사용자 대여 이력 조회
    @GetMapping("/me")
    public void getMyRentals(){

    }


}
