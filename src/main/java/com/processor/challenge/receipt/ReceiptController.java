package com.processor.challenge.receipt;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ReceiptController {

    private final ReceiptProcessor receiptProcessor;

    public ReceiptController(ReceiptProcessor receiptProcessor) {
        this.receiptProcessor = receiptProcessor;
    }

    /**
     * Default page to describe function
     * 
     * @return
     */
    @RequestMapping("/")
    public String home() {
        return "Receipt Processor API";
    }

    @PostMapping("/receipts/process")
    public ResponseEntity<Map<String, String>> processReceipt(@RequestBody Receipt receipt) {
        try {
            String id = receiptProcessor.processReceipt(receipt);
            Map<String, String> response = new HashMap<>();
            response.put("id", id);
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "Invalid JSON receipt input"));
        }
    }

    @GetMapping("/receipts/{id}/points")
    public ResponseEntity<Map<String, ?>> getPoints(@PathVariable String id) {
        try {
            Integer points = receiptProcessor.getPoints(id);
            Map<String, Integer> response = new HashMap<>();
            response.put("points", points);
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Map.of("error", "Receipt ID not found: " + id)); 
        }
    }
}
