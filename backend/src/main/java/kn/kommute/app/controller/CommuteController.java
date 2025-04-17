package kn.kommute.app.controller;

import kn.kommute.app.dto.CommuteDTO;
import kn.kommute.app.mapper.CommuteMapper;
import kn.kommute.app.model.Commute;
import kn.kommute.app.service.CommuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commutes")
public class CommuteController {

    @Autowired
    private CommuteService commuteService;

    // Add a new commute
    @PostMapping()
    public ResponseEntity<CommuteDTO> addCommute(@RequestBody CommuteDTO commuteDTO) {
        CommuteDTO addedCommute = commuteService.addCommute(commuteDTO);
        return ResponseEntity.ok(addedCommute);
    }

    // Get all commutes
    @GetMapping()
    public ResponseEntity<List<CommuteDTO>> getAllCommutes() {
        List<CommuteDTO> commutes = commuteService.getAllCommutes();
        return ResponseEntity.ok(commutes);
    }
}
