import { Review } from '../interfaces/Review.interface';
import { ReviewCreateRequest } from '../interfaces/ReviewCreateRequest.interface';
import { ReviewDetails } from '../interfaces/ReviewDetails.interface';
import axios from './config/axios';

const ReviewService = {
    getReviews: () => {
        return axios.get<Review[]>("/reviews");
    },
    getReview: (id: number) => {
        return axios.get<Review>(`/reviews/${id}`);
    },
    getReviewsByIds: (ids: number[]) => {
        const params = new URLSearchParams();
        ids.forEach(id => params.append('ids', id.toString()));
        return axios.get<ReviewDetails[]>('/reviews/ids', { params });
      },
      getReviewBySalon: (id: number) => {
        return axios.get<Review []>(`/reviews/for-salon/${id}`);
    },
    async createReview(review: ReviewCreateRequest) {
        try {
            const response = await axios.post('/reviews/add', review);
            return response.data;
        } catch (error) {
        }
        },
  
};

export default ReviewService;

