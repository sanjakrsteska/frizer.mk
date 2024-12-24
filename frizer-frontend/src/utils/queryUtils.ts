import { QueryParams } from '../interfaces/QueryParams.interface';

export const buildQueryString = (params: QueryParams) => {
    const queryParams = new URLSearchParams();
    for (const [key, value] of Object.entries(params)) {
      queryParams.append(key, value || '');
    }
    return queryParams.toString();
};