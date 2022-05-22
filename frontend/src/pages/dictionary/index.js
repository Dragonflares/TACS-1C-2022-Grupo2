import React, { Component, useEffect } from 'react'
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import { getLanguages } from '../../services/languageService';
import { ToastContainer, toast } from 'react-toastify';

import { getMeaning } from '../../services/dictionaryService';
export class Dictionary extends Component {
    constructor(props){
        super(props);
        this.state = {
            language: 'ENGLISH',
            search: '',
            result: null,
            languages: [],
        }
    }

    componentDidMount(){
        getLanguages().then(response => {
                this.setState({
                    languages: response.data.response.languages,
                });
        }).catch(e => {
            toast.error(e.response.data.response.message);
        })
    } 

    handleChange= (event) => {
        const target = event.target;
        const value = target.value;
        const name = target.name;

        this.setState ({
            [name] : value,
        });
    }

    handleSubmit = (event) => {
        event.preventDefault();
        event.stopPropagation();

        const {language, search} = this.state;

        getMeaning(search.toLowerCase(), language).then(response => {
                this.setState({
                    search: search,
                    result: response.data.response.phrase
                })
        }).catch(e => {
            console.log(e)
            toast.error(e.response.data.response.message);
        })
    }

    render() {
        return (
            <div className='p-5 mb-100 bg-light text-black'>
                <Col xs={12} md={{offset: 3, span: 6}}>
                    <Container fluid>
                        <Card  className="py-2">
                            <Card.Body>
                                <Card.Title>Dictionary</Card.Title>
                                <Form onSubmit={this.handleSubmit}>
                                    <Row>
                                        <Col xs={12} md={3} className="py-1">
                                            <Form.Group controlId="languageControl">
                                                <Form.Select name='language' 
                                                    value={this.state.language} 
                                                    onChange={this.handleChange}>
                                                    {this.state.languages.map(lang => (
                                                        <option key={lang} value={lang}>{lang}</option>
                                                    ))}
                                                </Form.Select>
                                            </Form.Group>
                                        </Col>
                                        <Col xs={12} md={7}  className="py-1">
                                            <Form.Group controlId="searchControl">
                                                <Form.Control type='text' name='search' placeholder='Word Search'
                                                    value={this.state.search} 
                                                    onChange={this.handleChange}/>                                                    
                                                <Form.Text className="text-muted"></Form.Text>
                                            </Form.Group>
                                        </Col>
                                        <Col md={2} className="py-1">
                                            <div className={"d-grid gap-2"}>
                                                <Button type="submit">Search</Button>
                                            </div>
                                        </Col>
                                    </Row>
                                </Form>
                            </Card.Body>
                        </Card>
                        {
                            this.state.result?
                            <>
                                <Card  className="py-2">
                                    <Card.Body>
                                        <Card.Title>
                                            {this.state.meaning}
                                        </Card.Title>
                                        <Card.Text>
                                            {this.state.result}
                                        </Card.Text>
                                    </Card.Body>
                                </Card>
                            </>
                            :
                            <>
                            </>
                        }
                    </Container>
                </Col>
                <ToastContainer/>
            </div>
        );
    }
}

export default Dictionary