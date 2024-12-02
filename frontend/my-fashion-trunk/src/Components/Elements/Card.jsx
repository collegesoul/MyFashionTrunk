function Card() {

    let status = "Accepted"
    let statusColour = "bg-green-600"

    return(
        <div className="card">
            <h2>Title here</h2>
            <div className="bg-amber-700 w-auto h-44 my-4"></div>
            <div>
                <span className={`${statusColour} h-3 w-3 mr-2 rounded-full inline-block`}></span>
                <span>Status: {status}</span>
            </div>
        </div>
    );
}

export default Card