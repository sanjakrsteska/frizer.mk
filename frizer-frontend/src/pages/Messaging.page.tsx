import {User} from '../context/Context';
import Navbar from '../components/fragments/Navbar/Navbar.component';
import Footer from '../components/fragments/Footer/Footer.component';
import Chat from '../components/Messaging/Chat/Chat.component';

interface MessagesPageProps {
    user?: User | null;
}

function MessagesPage({user}: MessagesPageProps) {
    return (
        <div>
            <h1>Messages</h1>
            {user ? (
                <>
                    <Navbar/>
                    <Chat user={user}/>
                    <Footer/>
                </>
            ) : (
                <p>Please log in to view messages.</p>
            )}
        </div>
    );
};

export default MessagesPage;
