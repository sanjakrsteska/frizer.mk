import { useSearchParams } from 'react-router-dom';

export const useQueryParams = () => {
  const [searchParams] = useSearchParams();
  return {
    name: searchParams.get('name') || '',
    city: searchParams.get('city') || '',
    distance: searchParams.get('distance') || '',
    rating: searchParams.get('rating') || '',
    latitude: searchParams.get('latitude') || '',
    longitude: searchParams.get('longitude') || '',
  };
};
