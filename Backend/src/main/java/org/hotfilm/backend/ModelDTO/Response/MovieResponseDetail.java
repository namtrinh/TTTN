package org.hotfilm.backend.ModelDTO.Response;

import lombok.*;
import org.hotfilm.backend.Model.Movie;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class MovieResponseDetail implements Serializable {
    private String movieId;
    private String movieName; // Tên phim
    private String posterUrl; // Đường dẫn hình ảnh poster
    private String movieTitle; // Tên phụ (nếu có)

    private String genre; // Thể loại (hành động, hài, lãng mạn, ...)
    private String director; // Tên đạo diễn
    private String country; // Quốc gia sản xuất
    private LocalDate releaseDate; // Ngày phát hành
    private int duration; // Thời lượng (phút)
    private double rating; // Đánh giá trung bình
    private String productionCompany; // Công ty sản xuất

    private String trailerUrl; // Đường dẫn tới trailer của phim

    private Movie.MovieStatus movieStatus;

    private Set<ShowtimeResponse> showtimes;
}
