import {MessageSimpleDTO } from '../interfaces/Message.interface';
import axios from './config/axios';

const MessageService = {
    getAllConversation: (userId:number) => {
        return axios.get<MessageSimpleDTO[]>(`/messages/all-conversation/${userId}`);
    },
    getAllConversationBetween:(userId1:number,userId2:number) => {
        return axios.get<MessageSimpleDTO[]>(`/messages/between/${userId1}/${userId2}`);

    },
    markMessagesAsRead:(senderId:number,receiverId:number) => {
        return axios.put<MessageSimpleDTO[]>(
            `/messages/mark-as-read`, 
            { senderId, receiverId } 
        );    }

};

export default MessageService;