import Button from "./Elements/Button.jsx";
import Card from "./Elements/Card.jsx";

function MyListings() {
    // const icon = <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5"
    //                   stroke="currentColor" className="size-5">
    //     <path strokeLinecap="round" strokeLinejoin="round" d="M12 4.5v15m7.5-7.5h-15"/>
    // </svg>
    const funnel = <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5}
                        stroke="currentColor" className="size-4">
        <path strokeLinecap="round" strokeLinejoin="round"
              d="M12 3c2.755 0 5.455.232 8.083.678.533.09.917.556.917 1.096v1.044a2.25 2.25 0 0 1-.659 1.591l-5.432 5.432a2.25 2.25 0 0 0-.659 1.591v2.927a2.25 2.25 0 0 1-1.244 2.013L9.75 21v-6.568a2.25 2.25 0 0 0-.659-1.591L3.659 7.409A2.25 2.25 0 0 1 3 5.818V4.774c0-.54.384-1.006.917-1.096A48.32 48.32 0 0 1 12 3Z"/>
    </svg>


    return (
        <>
            <div className="flex justify-between items-center">
                <h1>My Listings</h1>
                <span className="flex gap-x-5 items-center">
                    <span className="flex items-center gap-x-1 cursor-pointer border-2 border-gray-500 py-2 px-4 rounded-xl">
                        {funnel}
                        Filter
                    </span>
                    <Button text="Upload"/>
                </span>
            </div>
            <div className="mt-6">
                <div className="lg:grid lg:grid-cols-4 lg:gap-y-4 lg:gap-x-0 gap-3 flex flex-wrap justify-center text-center">
                    <Card/>
                    <Card/>
                    <Card/>
                    <Card/>
                    <Card/>
                    <Card/>
                </div>
            </div>
        </>
    );
}

export default MyListings