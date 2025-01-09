package org.hotfilm.identityservice.ModelDTO.Request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hotfilm.identityservice.Model.Movie;
import org.hotfilm.identityservice.Model.Showtime;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieCreateRequest{

    private String movieName; // Tên phim
    private String movieTitle; // Tên phụ (nếu có)
    private String movieDescription; // Mô tả ngắn về nội dung phim

    private String genre; // Thể loại (hành động, hài, lãng mạn, ...)
    private String director; // Tên đạo diễn
    private String country; // Quốc gia sản xuất
    private LocalDate releaseDate; // Ngày phát hành
    private int duration; // Thời lượng (phút)
    private double rating; // Đánh giá trung bình

    private String productionCompany; // Công ty sản xuất

    private String trailerUrl; // Đường dẫn tới trailer của phim

    private Movie.MovieStatus movieStatus;

    public enum MovieStatus{
        SHOWING, FINISH
    }
}
