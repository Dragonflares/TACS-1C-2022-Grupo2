import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';

import {Navbar, Nav, NavDropdown, Container} from 'react-bootstrap';
import { IsAuthenticated } from './services/appService';
import {  BrowserRouter as Router,  Routes,  Route} from "react-router-dom";

import LogIn from './pages/login/index';
import Home from './pages/home/index';
class App extends Component {
  constructor(props){
    super(props);
    this.state = {
      authorized: true
    }
  }

  componentDidMount(){
    //this.setState({authorized :  IsAuthenticated()});
  }

  render(){
    return(
      
      <div className="App">
        {
          this.state.authorized?
          <>
            <Navbar bg="light" expand="lg">
              <Container>
                <Navbar.Brand href="#home">Wordle</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                  <Nav className="me-auto">
                    <Nav.Link href="#home">Home</Nav.Link>
                    <Nav.Link href="#link">Link</Nav.Link>
                    <NavDropdown title="Dropdown" id="basic-nav-dropdown">
                      <NavDropdown.Item href="#action/3.1">Action</NavDropdown.Item>
                      <NavDropdown.Item href="#action/3.2">Another action</NavDropdown.Item>
                      <NavDropdown.Item href="#action/3.3">Something</NavDropdown.Item>
                      <NavDropdown.Divider />
                      <NavDropdown.Item href="#action/3.4">Separated link</NavDropdown.Item>
                    </NavDropdown>
                  </Nav>
                </Navbar.Collapse>
              </Container>
            </Navbar>
          </>
          : 
          <>
          </>
        }
        <Router>
          <Routes>
            <Route path='/' element={<Home/>} />
            <Route path='/log-in' element={<LogIn/>} />
          </Routes>  
        </Router>
      </div>
    );     
  }
 
}

export default App;
