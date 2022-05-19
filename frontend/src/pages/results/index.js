import React, {useState, useCallback, useEffect} from "react";
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import { getLangauges } from "../../services/languageService";
import { getDailyResults, updateDailyResults } from "../../services/userService";

export function Results () {
    const [language, setLanguage] = useState('');
    const [result, setResult] = useState(0);
    const [validated, setValidated] = useState(false);
    const [languages, setLanguages] = useState([]);
    const [results, setResults] = useState([]);

    const init = () => {
        getLangauges().then(response => {
            if(response.status === 200){
                setLanguages(response.data);
            }
        });

        getDailyResults().then(response => {
            if(response.status === 200){
                setResults(response.data);
                setLanguage(response.data[0].language);
            }
        });
    };

    useEffect(() => {
        init();
    }, []);

    const handleSubmit = useCallback(async (event) => {       
        event.preventDefault();
        event.stopPropagation();
    
        if(!validated){
            setValidated(validated => !validated);
        }
    
        if(!language || !result || language === '')
          return;
    
        updateDailyResults({
            language: language,
            result: result
        }).then((response) => {
            if(response.status === 200){
                init();
            }
        });
    });

    const handleLangChange = useCallback((event) => {
        const  value = event.target.value;

        const langResult = results.find((r) => (r.lang === value));

        setResult(langResult.result);
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
                                                    onChange={handleLangChange}>
                                                    {languages.map(lang => (
                                                        <option key={lang.id} value={lang.id}>{lang.desc}</option>
                                                    ))}
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