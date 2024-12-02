// eslint-disable-next-line react/prop-types
function Container({children}) {

    return (
        <div className="lg:mx-24 mx-10 my-6">
            {children}
        </div>
    );

}

export default Container