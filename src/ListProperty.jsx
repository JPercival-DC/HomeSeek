import { Link } from "react-router-dom";

function ListProperty() {
    return(
        <div>
            <section className="hero-v2">
                <h1>List Property</h1>
                <p>List your property for sale or rent.
                    Coming soon.
                </p>
                <div>
                   <Link to="/">Home</Link>
                </div>
            </section>
           </div>
    );


}


export default ListProperty;