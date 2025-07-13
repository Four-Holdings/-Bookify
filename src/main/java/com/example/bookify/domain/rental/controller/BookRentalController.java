package com.example.bookify.domain.rental.controller;


import com.example.bookify.domain.rental.service.dto.BookRentalResponseDto;
import com.example.bookify.domain.rental.service.dto.BookRentalStatusDto;
import com.example.bookify.domain.rental.service.service.BookRentalService;
import com.example.bookify.global.common.apiresponse.ResponseDto;
import com.example.bookify.global.security.jwt.CustomPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RequestMapping("/api/rentals")
@RestController
@RequiredArgsConstructor
public class BookRentalController {

    private final BookRentalService rentalService;


    //1. 도서 대여 처리
    @PostMapping("/{bookId}")
    public ResponseEntity<ResponseDto<BookRentalResponseDto>> rentBook(
            @PathVariable Long bookId,
            @AuthenticationPrincipal CustomPrincipal principal
    ) {
        Long userId = principal.getUserId();
        BookRentalResponseDto response =  rentalService.rentBook(bookId, userId);
        return ResponseEntity.ok(new ResponseDto<>("도서 대여가 완료되었습니다.", response));
    }


    //2. 도서 대여 반납
    @PatchMapping("/{rentalId}/return")
    public ResponseEntity<ResponseDto<BookRentalResponseDto>> returnBook(
            @PathVariable Long rentalId,
            @AuthenticationPrincipal CustomPrincipal principal
    ) {
        Long userId = principal.getUserId();
        BookRentalResponseDto response = rentalService.returnBook(rentalId, userId);
        return ResponseEntity.ok(new ResponseDto<>("도서 반납이 완료되었습니다.", response));
    }




    //3.도서 상태 조회
    @GetMapping("/status")
    public ResponseEntity<ResponseDto<BookRentalStatusDto>> getRentalStatus(@RequestParam Long bookId) {
        BookRentalStatusDto response = rentalService.getRentalStatus(bookId);
        return ResponseEntity.ok(new ResponseDto<>("도서 대여 상태 조회 성공", response));
    }



    //4.사용자 대여 이력 조회
    @GetMapping("/me")
    public ResponseEntity<ResponseDto<List<BookRentalResponseDto>>> getMyRentalHistory(
            @AuthenticationPrincipal CustomPrincipal principal) {
        List<BookRentalResponseDto> response = rentalService.getMyRentalHistory(principal.getUserId());
        return ResponseEntity.ok(new ResponseDto<>("내 대여 이력 조회 성공", response));
    }


}
