/**
 * A container component that provides consistent margin for child components
 * @param {props} children the child elements to be rendered in the container
 * @returns {JSX.Element} the rendered container component
 * **/
// eslint-disable-next-line react/prop-types
function Container({children}) {

    return (
        <div className="lg:mx-24 mx-10 my-6">
            {children}
        </div>
    );

}

export default Container