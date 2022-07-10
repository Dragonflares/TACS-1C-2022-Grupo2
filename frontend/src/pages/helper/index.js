import React, { useReducer, useCallback, useEffect } from 'react'
import {
    Tabs, Tab, Card, Container, Row, Button,
    Col, Form, InputGroup, FloatingLabel
} from "react-bootstrap";
import { getLanguages } from '../../services/languageService';
import { getHelperWord } from '../../services/helperService';
import { ToastContainer, toast } from 'react-toastify';

const initialValues = {
    form: {
        greenWords: {
            0:'',
            1:'',
            2:'',
            3:'',
            4:''
        },
        yellowWords: '',
        greyWords: '',
        language: 'ENGLISH',
        fromDictionary: false,
    },
    data: '',
    searchExecuted: false,
    languages: []
}

export function Helper() {   

    const [state, dispatch] = useReducer(reducer, initialValues); 

    useEffect(() => {
        const Init = () => {
            getLanguages().then(response => {
                dispatch({type: 'setLanguages', value: response.data.response.languages});
            }).catch(e => {
                toast.error(e.response.data.response.message);
            })
        }

        Init();
    }, [])

    const getWord = () => {
        getHelperWord(state.form).then(
            response => {
                    var finalPhrase = ''
                    response.data.response.forEach(element => {
                        finalPhrase += element.phrase + ','
                    })
                    dispatch({type: 'setData', value: finalPhrase});
            }
        ).catch( e => {
            toast.error(e.response.data.response.message);
        })
    }

    const handleSubmit = () => {
        getWord()
        dispatch({type: 'toggleSearch'});
    }

    const handleLanguageChange = useCallback((event) => {
        const value = event.target.value;

        dispatch({type: 'setLanguage', value: value});
    })

    const handleDictSwitchChange = useCallback((event) => {
        const value = event.target.value === 'on';

        dispatch({type: 'setFromDictionary', value: value});
    })

    const handlegreyWordsChange = useCallback((event) => {
        const value = event.target.value;
        dispatch({type: 'setGreyWords', value: value});
    })

    const handleyellowWordsChange = useCallback((event) => {
        const value = event.target.value;
        dispatch({type: 'setYellowWords', value: value});
    })

    const handleGreenLetters0 = useCallback((event) => {
        ManageGreenWord(event,"0")
    }) 
    const handleGreenLetters1 = useCallback((event) => {
        ManageGreenWord(event,"1")
    }) 
    const handleGreenLetters2 = useCallback((event) => {
        ManageGreenWord(event,"2")
    }) 
    const handleGreenLetters3 = useCallback((event) => {
        ManageGreenWord(event,"3")
    }) 
    const handleGreenLetters4 = useCallback((event) => {
        ManageGreenWord(event,"4")
    }) 

    function ManageGreenWord(event, columnName){
        const value = event.target.value;

        dispatch({type: 'setGreenWords', column: columnName, value: value});
    }


    return (
        <div className='p-5 mb-100 bg-light text-black'>
            <Col xs={12} md={{ offset: 1, span: 10 }}>
                <Container fluid>
                    <Card className="py-2">
                        <Card.Body>
                            <Card.Title>Helper</Card.Title>
                            <Form>
                                <Row>
                                    <Row>
                                        <Col md={6} xs={12} className="py-1">
                                            <Row>
                                            <Form.Group controlId="yellowWordsControl">
                                                <Form.Control type='text' name='yellowWords' placeholder='Yellow Letters'
                                                    value={state.form.yellowWords}
                                                    onChange={handleyellowWordsChange} />
                                                <Form.Text className="text-muted"></Form.Text>
                                            </Form.Group>
                                            </Row>
                                            <Row>
                                            <Form.Group controlId="greyWordsControl">
                                                <Form.Control type='text' name='greyWords' placeholder='Grey letters'
                                                    value={state.form.greyWords}
                                                    onChange={handlegreyWordsChange} />
                                                <Form.Text className="text-muted"></Form.Text>
                                            </Form.Group>
                                            </Row>
                                        </Col>
                                        <Col md={4} xs={12}  className="py-1">
                                            <Form.Group className='_6lux' controlId="formHelperLang">
                                                <InputGroup>
                                                    <FloatingLabel className='group-first-element'>
                                                        {
                                                            <Form.Select
                                                                name="language" required
                                                                value={state.form.language}
                                                                onChange={handleLanguageChange}>
                                                                {state.languages.map(lang => (
                                                                    <option key={lang} value={lang}>{lang}</option>
                                                                ))}
                                                            </Form.Select>
                                                        }
                                                        <label style={{ paddingLeft: 0, marginLeft: '1em' }}>Language</label>
                                                    </FloatingLabel>
                                                </InputGroup>
                                            </Form.Group>
                                        </Col>
                                        <Col md={2} xs={12} className="py-1">
                                            <Form.Group>
                                                <InputGroup className='form-check-input-group'>
                                                    <Form.Check 
                                                            type="switch"
                                                            id="dict"
                                                            name="fromDictionary"
                                                            className='form-switch-md'
                                                            onChange={handleDictSwitchChange}
                                                        />
                                                    <label >Use Dictionary</label>
                                                </InputGroup>
                                            </Form.Group>
                                        </Col>
                                    </Row>
                                    <Row>

                                    </Row>
                                        <label>Green Letters</label>

                                    <Row>
                                        <Col className="py-1">
                                            <input name = 'GreenLetter0' type="text" maxLength="1"  className="form-control form-control-sm small-input"
                                            onChange = {handleGreenLetters0}
                                            />
                                        </Col>
                                        <Col  className="py-1">
                                            <input name = 'GreenLetter1' type="text" maxLength="1"  className="form-control form-control-sm small-input"
                                            onChange = {handleGreenLetters1}
                                            />
                                        </Col>
                                        <Col  className="py-1">
                                            <input name = 'GreenLetter2' type="text" maxLength="1"  className="form-control form-control-sm small-input"
                                            onChange = {handleGreenLetters2}
                                            />
                                        </Col>
                                        <Col  className="py-1">
                                            <input name = 'GreenLetter3' type="text" maxLength="1"  className="form-control form-control-sm small-input"
                                            onChange = {handleGreenLetters3}
                                            />
                                        </Col>
                                        <Col  className="py-1">
                                            <input name = 'GreenLetter4' type="text" maxLength="1"  className="form-control form-control-sm small-input"
                                            onChange = {handleGreenLetters4}
                                            />
                                        </Col>                                        
                                    </Row>
                                    <div className="col-xs-1 text-center">
                                        <br></br>
                                            <Button type="button" onClick={handleSubmit}>Search</Button>
                                    </div>
                                </Row>
                            </Form>
                        </Card.Body>
                    </Card>
                    {
                            state.data !== "" ?
                            <>
                                <Card  className="py-2">
                                    <Card.Body>
                                        <Card.Title>
                                            {"Possible Words"}
                                        </Card.Title>
                                        <Card.Text>
                                            {state.data}
                                        </Card.Text>
                                    </Card.Body>
                                </Card>
                            </>
                            : state.searchExecuted ?
                            <>
                                <Card  className="py-2">
                                    <Card.Body>
                                        <Card.Title>
                                            {"Possible Words"}
                                        </Card.Title>
                                        <Card.Text>
                                            {"There are no possible words under these parameters"}
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
    )
}

function reducer(state, action){
    var regex = /^[A-Za-z]*$/

    switch (action.type) {
        case 'setLanguage': return {
            ...state,
            form: {
                ...state.form,
                language: action.value
            }
        }
        case 'setGreenWords': return {
            ...state,
            form: {
                ...state.form,
                greenWords: {
                    ...state.form.greenWords,
                    [action.column]: action.value,
                }
            }
        }
        case 'setYellowWords': {
            
            if(!regex.test(action.value))
                return state;

            return {
                ...state,
                form: {
                    ...state.form,
                    yellowWords: action.value
                }
            };
        }
        case 'setGreyWords': {
            if(!regex.test(action.value))
                return state;
            
            return {
                ...state,
                form: {
                    ...state.form,
                    greyWords: action.value
               }
            };
        }
        case 'setData': return {
            ...state,
            data: action.value
        }
        case 'toggleSearch': return {
            ...state,
            searchExecuted: !state.searchExecuted
        }
        case 'setFromDictionary': return {
            ...state,
            form: {
                ...state.form,
                fromDictionary: action.value
            }
        }
        case 'setLanguages': return {
            ...state,
            languages: action.value
        }
        default: throw new Error();
    }
}

export default Helper;