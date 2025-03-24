package org.hotfilm.backend.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "movie")
public class Movie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "movie_id")
    private String movieId; // UUID để đảm bảo tính duy nhất

    private String movieName; // Tên phim
    private String movieTitle; // Tên phụ (nếu có)
    private String movieDescription; // Mô tả ngắn về nội dung phim

    private String genre; // Thể loại (hành động, hài, lãng mạn, ...)
    private String director; // Tên đạo diễn
    private String country; // Quốc gia sản xuất
    private LocalDate releaseDate; // Ngày phát hành
    private int duration; // Thời lượng (phút)
    private double rating; // Đánh giá trung bình
    private String posterUrl; // Đường dẫn hình ảnh poster

    private String productionCompany; // Công ty sản xuất

    private String trailerUrl; // Đường dẫn tới trailer của phim

    private MovieStatus movieStatus;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
    private Set<Showtime> showtimes;

    public enum MovieStatus{
        SHOWING, FINISH
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieId='" + movieId + '\'' +
                ", movieName='" + movieName + '\'' +
                ", movieTitle='" + movieTitle + '\'' +
                ", movieDescription='" + movieDescription + '\'' +
                ", genre='" + genre + '\'' +
                ", director='" + director + '\'' +
                ", country='" + country + '\'' +
                ", releaseDate=" + releaseDate +
                ", duration=" + duration +
                ", rating=" + rating +
                ", posterUrl='" + posterUrl + '\'' +
                ", productionCompany='" + productionCompany + '\'' +
                ", trailerUrl='" + trailerUrl + '\'' +
                ", movieStatus=" + movieStatus +
                '}';
    }
}
