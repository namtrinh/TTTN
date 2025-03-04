package org.hotfilm.identityservice.ModelDTO.Response;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class MovieResponse implements Serializable {
    private String movieId;
    private String movieName; // Tên phim
    private String posterUrl; // Đường dẫn hình ảnh poster
    private String movieTitle; // Tên phụ (nếu có)

 //   private Set<ShowtimeResponse> showtimes;
}
