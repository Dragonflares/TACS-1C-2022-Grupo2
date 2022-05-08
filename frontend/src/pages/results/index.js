import React, {useState, useCallback, useEffect} from "react";
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';

const englishLang = 'en';
const spanishLang = 'es';

export function Results () {
    const [language, setLanguage] = useState('');
    const [result, setResult] = useState(0);
    const [validated, setValidated] = useState(false);

    const handleSubmit = useCallback(async (event) => {       
        event.preventDefault();
        event.stopPropagation();
    
        if(!validated){
            setValidated(validated => !validated);
        }
    
        if(!language || !result || language === '')
          return;  

        
    });

    return(
        <div className='p-5 mb-100 bg-light text-black'>
                <Col xs={12} md={{offset: 3, span: 6}}>
                    <Container fluid>
                        <Card  className="py-2">
                            <Card.Body>
                                <Card.Title>Load Result</Card.Title>
                                <Form onSubmit={handleSubmit} noValidate validated={validated}>
                                    <Row>
                                        <Col xs={12} md={3} className="py-1">
                                            <Form.Group controlId="languageControl">
                                                <Form.Select name='language' 
                                                    value={language} 
                                                    onChange={(event) => {setLanguage(event.target.value)}}>
                                                    <option value={englishLang}>English</option>
                                                    <option value={spanishLang}>Español</option>
                                                </Form.Select>
                                            </Form.Group>
                                        </Col>
                                        <Col xs={12} md={7}  className="py-1">
                                            <Form.Group controlId="searchControl">
                                                <Form.Control type='number' name='result' placeholder='Result'
                                                    value={result} min={0} max={7}
                                                    onChange={(event) => {setResult(event.target.value)}}/>
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
                    </Container>
                </Col>
            </div>
    );
}

export default Results