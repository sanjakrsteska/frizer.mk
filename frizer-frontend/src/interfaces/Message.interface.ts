export interface MessageSimpleDTO {
    id: number;
    timestamp: Date;
    senderId: number;  
    content: string;
    receiverId: number;
    read: boolean
}