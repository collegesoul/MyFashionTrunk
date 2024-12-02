import {BrowserRouter as Router, Route, Routes} from "react-router";
import Navigation from "./Components/Navigation.jsx";
import Container from "./Components/Elements/Container.jsx";
import MyListings from "./Components/MyListings.jsx";
import Categories from "./Components/Categories.jsx";

function App() {
    return(
        <Router>
            <Navigation/>
            <Container>
                <main className="mt-20">
                    <Routes>
                        <Route path="/" element={<MyListings/>}/>
                        <Route path="/categories" element={<Categories/>}/>
                    </Routes>
                </main>
            </Container>
        </Router>
    );
}

export default App
