const ImageService = {
    getFullSalonImageUrl: (id: number, backgroundImageId: number): string => {
        const baseUrl = 'http://localhost:8080/api/salons';
        return `${baseUrl}/${id}/image/${backgroundImageId}`;    
    }
};

export default ImageService;