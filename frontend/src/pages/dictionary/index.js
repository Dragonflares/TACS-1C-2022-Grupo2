import React, { useState, useCallback, useEffect } from 'react'
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import { getLanguages } from '../../services/languageService';
import { ToastContainer, toast } from 'react-toastify';

import { getMeaning } from '../../services/dictionaryService';

export function Dictionary(){

    const [formRequest, setFormRequest] = useState({
        language: 'ENGLISH',
        search: '',
    });

    const [result, setResult] = useState({
        word: '',
        meaning: ''
    });
    const [languages, setLanguages] = useState([])
    
    useEffect(() => {
        const init = () => {
            getLanguages().then(response => {
                setLanguages(response.data.response.languages);
            }).catch(e => {
                toast.error(e.response.data.response.message);
            })
        }

        init();
    }, []);

    const handleChange = useCallback((e) => {
        const { name, value } = e.target;

        setFormRequest(prevState => ({
            ...prevState,
            [name]: value
        }));
    });

    const handleSubmit = useCallback((event) => {
        event.preventDefault();
        event.stopPropagation();

        getMeaning(formRequest.search.toLowerCase(), formRequest.language).then(response => {
            setFormRequest(prevState => ({
                ...prevState,
                search: formRequest.search,
            }));

            setResult({
                word: formRequest.search,
                meaning: response.data.response.phrase
            });
        }).catch(e => {
            console.log(e)
            toast.error(e.response.data.response.message);
        })
    });
     

    return (
        <div className='p-5 mb-100 bg-light text-black'>
            <Col xs={12} md={{offset: 3, span: 6}}>
                <Container fluid>
                    <Card  className="py-2">
                        <Card.Body>
                            <Card.Title>Dictionary</Card.Title>
                            <Form onSubmit={handleSubmit}>
                                <Row>
                                    <Col xs={12} md={3} className="py-1">
                                        <Form.Group controlId="languageControl">
                                            <Form.Select name='language' 
                                                value={formRequest.language} 
                                                onChange={handleChange}>
                                                {languages.map(lang => (
                                                    <option key={lang} value={lang}>{lang}</option>
                                                ))}
                                            </Form.Select>
                                        </Form.Group>
                                    </Col>
                                    <Col xs={12} md={7}  className="py-1">
                                        <Form.Group controlId="searchControl">
                                            <Form.Control type='text' name='search' placeholder='Word Search'
                                                value={formRequest.search} 
                                                onChange={handleChange}/>                                                    
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
                        result.word !== ''?
                        <>
                            <Card  className="py-2">
                                <Card.Body>
                                    <Card.Title>
                                        {result.word}
                                    </Card.Title>
                                    <Card.Text>
                                        {result.meaning}
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

export default Dictionary