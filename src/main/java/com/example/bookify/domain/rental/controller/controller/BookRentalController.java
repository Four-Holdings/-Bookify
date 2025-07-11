package com.example.bookify.domain.rental.controller.controller;

import com.example.bookify.domain.rental.service.dto.BookRentalResponseDto;
import com.example.bookify.domain.rental.service.service.BookRentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RequestMapping("/api/rentals")
@RestController
@RequiredArgsConstructor
public class BookRentalController {

    private final BookRentalService rentalService;


    //1. 도서 대여 처리
    @PostMapping("/{bookId}")
    public ResponseEntity<BookRentalResponseDto> rentalBook(@PathVariable Long bookId, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(rentalService.rentBook(bookId, userId));
    }


    //2. 도서 대여 반납
    @PatchMapping("/{rentalId}/return")
    public void returnBook(@PathVariable Long rentalId, Principal principal){
        ResponseEntity<String> response = new ResponseEntity<>("success",HttpStatus.OK);

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
