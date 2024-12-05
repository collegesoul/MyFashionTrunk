import {BrowserRouter as Router, Route, Routes} from "react-router";
import Login from "./Components/Login.jsx";
import Register from "./Components/Register.jsx";
import MainLayout from "./Components/MainLayout.jsx";

function App() {
    return(
        <Router>
            <Routes>
                <Route path="/login" element={<Login/>}/>
                <Route path="/register" element={<Register/>}/>
                <Route path="*" element={<MainLayout/>}/>
            </Routes>

        </Router>
    );
}

export default App
