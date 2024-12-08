import {BrowserRouter as Router, Navigate, Route, Routes} from "react-router";
import Login from "./Components/Login.jsx";
import Register from "./Components/Register.jsx";
import MainLayout from "./Components/MainLayout.jsx";

function App() {
    const user = localStorage.getItem("user");
    return(
        <Router>
            <Routes>
                <Route path="/login" element={<Login/>}/>
                <Route path="/register" element={<Register/>}/>
                <Route path="*" element={user ? <MainLayout/> : <Navigate to="/login"/> }/>
            </Routes>

        </Router>
    );
}

export default App
