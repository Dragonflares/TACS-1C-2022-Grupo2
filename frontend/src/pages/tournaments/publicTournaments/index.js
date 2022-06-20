import React, {useReducer, useCallback, useEffect} from "react";
import Card from 'react-bootstrap/Card';
import Container from 'react-bootstrap/Container';
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';
import Image from 'react-bootstrap/Image';
import { Row } from "react-bootstrap";
import Modal from "react-bootstrap/Modal";
import { PaginatedTable } from "../../../shared/components/paginatedTable";
import { addParticipants, getPublicTournaments, getPublicTournamentsCount } from "../../../services/tournamentService";
import { getUserDataStruct } from '../../../services/userService'
import { ToastContainer, toast } from 'react-toastify';

const initialValues = {
    data: {
        elements: [],
        count: 10,
    },
    show: false,
    selected: {
        id: 0,
        name: '',
        owner: {username: ''},
    }
}

export function PublicTournaments () {
    const pageSize = 10;

    const headings = [
        {   
            name: 'name',
            show: 'Name'
        },
        {
            name: 'language',
            show: 'Language',
        },
        {
            name: 'startDate',
            show: 'Start Date'
        },
        {
            name: 'endDate',
            show: 'End Date'
        }
    ];

    const [state, dispatch] = useReducer(reducer, initialValues);

    const getData = (page, pageSize) =>  {
        getPublicTournaments(page, pageSize).then(
            response => {

                dispatch({type: 'setData', value:{
                    elements: response.data.response.elements,
                    count: response.data.response.totalCount
                }});
     
            }
        ).catch( e => {
                toast.error(e.response.data.response.message);
            })
    };

    useEffect(() => {
        const init = async () => {
            getData(1, pageSize);
        };

        init();
    }, []);

    const handlePageChange = useCallback(async (page, pageSize) => {
        getData(page, pageSize);
    });

    const handleRowClick = useCallback((element) => {
        dispatch({type: 'setSelected', value: element});
        dispatch({type: 'toogleShow'});
    });

    const handleHide = useCallback(() => {
        dispatch({type: 'toogleShow'});
        dispatch({type: 'resetSelected'});
    });

    const handleJoin = useCallback(() => {
        addParticipants(state.selected.id,getUserDataStruct()).then(
            response => {
                toast.success(`Successfully joined ${state.selected.name}!`);
                dispatch({type: 'resetSelected'});
            }
        ).catch(
            e => {            
                toast.error(e.response.data.response.message)
            }
        ).finally(() => {
            dispatch({type: 'toogleShow'});

        });
    });

    return (
        <>
            <Col xs={12} md={{offset: 3, span: 6}}>
                <Container fluid>
                    <Card>
                        <Card.Body>
                            <Card.Title>Public Tournaments</Card.Title>
                            {
                                state.data.count > 0 ?
                                    <>
                                        <PaginatedTable 
                                            headings={headings}
                                            data={state.data}
                                            pageSize={pageSize}
                                            onPageChange={handlePageChange}
                                            onClick={handleRowClick}
                                            key='publicTournaments'
                                        />
                                    </>
                                :
                                <Container fluid>
                                <div style={{marginTop: 5 +'%'}}>
                                    <Col xs={12} md={{offset: 2, span: 8}}>
                                        <Row>
                                            <Col xs={12} md={{offset: 3, span: 6}}>
                                                <Image src='images/404.png' fluid/>
                                            </Col>
                                        </Row>
                                        <Row>
                                            <Col>
                                                <i><p>No public tournaments found.</p>
                                                <p>Try creating a new one!</p></i>
                                            </Col>
                                        </Row>
                                                   
                                    </Col>
                                </div>                
                            </Container>
                            }
                        </Card.Body>
                    </Card>
                </Container>        
            </Col>
            <Modal size="sm" show={state.show} onHide={handleHide} backdrop="static" centered>
                <Modal.Header>
                    <Modal.Title>Join Tournament {state.selected.name}</Modal.Title>
                </Modal.Header>
                <Modal.Footer>
                    <Button variant="danger"  type={'button'} onClick={handleHide}>
                        NO
                    </Button>
                    <Button variant="primary" type={'button'} onClick={handleJoin}>
                        YES
                    </Button>
                </Modal.Footer>
            </Modal>
            <ToastContainer/>
        </>
    )
}

function reducer(state, action){
    switch(action.type){
        case 'setData' : return{
            ...state,
            data: action.value
        }
        case 'toogleShow' : return{
            ...state,
            show: !state.show
        }
        case 'resetSelected': return {
            ...state,
            selected: {
                id: 0,
                name: ''
            }
        }
        case 'setSelected' : return{
            ...state,
            selected: action.value
        }
        default: throw new Error();
    }
}

export default PublicTournaments;