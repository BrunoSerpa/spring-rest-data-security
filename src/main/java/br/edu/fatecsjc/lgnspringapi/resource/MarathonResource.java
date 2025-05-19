package br.edu.fatecsjc.lgnspringapi.resource;

import br.edu.fatecsjc.lgnspringapi.dto.MemberDTO;
import br.edu.fatecsjc.lgnspringapi.service.MarathonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Member and Marathons")
@SecurityRequirement(name = "bearerAuth")
public class MarathonResource {
        private MarathonService marathonService;

        public MarathonResource(MarathonService marathonService) {
                this.marathonService = marathonService;
        }

        @GetMapping
        @Operation(description = "Get all members and marathons", responses = {
                        @ApiResponse(description = "Success", responseCode = "200"),
                        @ApiResponse(description = "Unauthorized/Invalid token", responseCode = "403"),
                        @ApiResponse(description = "Unknown error", responseCode = "400"),
        })
        public ResponseEntity<List<MemberDTO>> getAllMembers() {
                return ResponseEntity.ok(marathonService.getAll());
        }

        @GetMapping("/{id}")
        @Operation(description = "Get a member and marathons by member ID", responses = {
                        @ApiResponse(description = "Success", responseCode = "200"),
                        @ApiResponse(description = "Unauthorized/Invalid token", responseCode = "403"),
                        @ApiResponse(description = "Unknown error", responseCode = "400"),
        })
        public ResponseEntity<MemberDTO> getMemberById(@PathVariable Long id) {
                return ResponseEntity.ok(marathonService.findById(id));
        }

        @PutMapping("/{id}")
        @PreAuthorize("hasRole('ADMIN')")
        @Operation(description = "Update a member and marathons by member ID", responses = {
                        @ApiResponse(description = "Success", responseCode = "201"),
                        @ApiResponse(description = "Unauthorized/Invalid token", responseCode = "403"),
                        @ApiResponse(description = "Unknown error", responseCode = "400"),
        })
        public ResponseEntity<MemberDTO> update(@PathVariable Long id, @RequestBody MemberDTO body) {
                return ResponseEntity.status(HttpStatusCode.valueOf(201))
                                .body(marathonService.save(id, body));
        }

        @PostMapping
        @PreAuthorize("hasRole('ADMIN')")
        @Operation(description = "Register a new member and marathons", responses = {
                        @ApiResponse(description = "Success", responseCode = "201"),
                        @ApiResponse(description = "Unauthorized/Invalid token", responseCode = "403"),
                        @ApiResponse(description = "Unknown error", responseCode = "400"),
        })
        public ResponseEntity<MemberDTO> register(@RequestBody MemberDTO body) {
                return ResponseEntity.status(HttpStatusCode.valueOf(201))
                                .body(marathonService.save(body));
        }

        @DeleteMapping("/{id}")
        @PreAuthorize("hasRole('ADMIN')")
        @Operation(description = "Delete a member and marathons by member ID", responses = {
                        @ApiResponse(description = "Success", responseCode = "204"),
                        @ApiResponse(description = "Unauthorized/Invalid token", responseCode = "403"),
                        @ApiResponse(description = "Unknown error", responseCode = "400"),
        })
        public ResponseEntity<Void> update(@PathVariable Long id) {
                marathonService.delete(id);
                return ResponseEntity.noContent().build();
        }
}