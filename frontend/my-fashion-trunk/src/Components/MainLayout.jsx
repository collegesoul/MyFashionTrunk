import Navigation from "./Navigation.jsx";
import Container from "./Elements/Container.jsx";
import {Route, Routes} from "react-router";
import MyListings from "./MyListings.jsx";
import Categories from "./Categories.jsx";
import Profile from "./Profile.jsx";
import Upload from "./Upload.jsx";

function MainLayout() {
    return(
        <>
            <Navigation/>
            <Container>
                <main className="mt-20">
                    <Routes>
                        <Route path="/" element={<MyListings/>}/>
                        <Route path="/categories" element={<Categories/>}/>
                        <Route path="/profile" element={<Profile/>}/>
                        <Route path="/create-new-listing" element={<Upload/>}/>
                    </Routes>
                </main>
            </Container>
        </>
    );
}

export default MainLayout