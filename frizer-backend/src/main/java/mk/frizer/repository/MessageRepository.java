package mk.frizer.repository;

import mk.frizer.domain.BaseUser;
import mk.frizer.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByReceiverIdOrSenderId(Long senderId, Long receiverId);

    List<Message> findAllByReceiverIdAndSenderId(Long receiverId, Long senderId);

    @Query("SELECT m FROM Message m WHERE (m.sender.id = :userId1 AND m.receiver.id = :userId2) OR (m.sender.id = :userId2 AND m.receiver.id = :userId1)")
    List<Message> findBySenderIdAndReceiverIdOrSenderIdAndReceiverId(@Param("userId1") Long userId1, @Param("userId2") Long userId2);

    @Modifying
    @Query("UPDATE Message m SET m.isRead = true WHERE m.sender.id = :senderId AND m.receiver.id = :receiverId AND m.isRead = false")
    void updateMessagesToRead(Long senderId, Long receiverId);
}
