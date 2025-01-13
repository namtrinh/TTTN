export class Movie {
  movieId!: string; // UUID để đảm bảo tính duy nhất
  movieName!: string; // Tên phim
  movieTitle?: string; // Tên phụ (nếu có)
  movieDescription?: string; // Mô tả ngắn về nội dung phim
  genre?: string; // Thể loại (hành động, hài, lãng mạn, ...)
  director?: string; // Tên đạo diễn
  country?: string; // Quốc gia sản xuất
  releaseDate?: Date; // Ngày phát hành
  duration?: number; // Thời lượng (phút)
  rating?: number; // Đánh giá trung bình
  posterUrl?: string; // Đường dẫn hình ảnh poster
  productionCompany?: string; // Công ty sản xuất
  trailerUrl?: string; // Đường dẫn tới trailer của phim
  movieStatus!: MovieStatus; // Trạng thái của phim
}

// Enum cho trạng thái phim
export enum MovieStatus {
  SHOWING = 'SHOWING',
  FINISH = 'FINISH',
}

