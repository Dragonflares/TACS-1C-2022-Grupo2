import React, {useReducer, useCallback, useEffect} from "react";
import Card from 'react-bootstrap/Card';
import Container from 'react-bootstrap/Container';
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';
import Image from 'react-bootstrap/Image';
import { PaginatedTable } from "../../../shared/components/paginatedTable";
import { Row } from "react-bootstrap";
import { Link } from "react-router-dom";
import OptionsPopUp from "./optionsPopUp";
import { getPublicTournaments } from "../../../services/tournamentService";
import { getTournaments, getTournamentsCount } from "../../../services/userService";
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

export function MyTournaments () {
    const pageSize = 10;

    const [state, dispatch] = useReducer(reducer, initialValues);

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
        },
        {
            name: 'privacy',
            show: 'Privacy'
        }
    ];

    const getData = (page, pageSize) =>  {
        getTournaments(page, pageSize).then(
            response => {
                dispatch({type: 'setData', value:{
                    elements: response.data.response.elements,
                    count: response.data.response.totalCount
                }});
            }
        ).catch(e => {
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
    });

    return (
        <>
            <Col xs={12} md={{offset: 3, span: 6}}>
                <Container fluid>
                    <Card>
                        <Card.Body>
                            <Card.Title>My Tournaments</Card.Title>
                            <Row>
                                <Button as={Link} to={"/tournament/create"} variant="primary" style={{float: 'left'}}>
                                    Create New Tournament
                                </Button>
                            </Row>
                            <Row>
                                {
                                    state.data.count > 0 ?
                                    <>
                                        <PaginatedTable 
                                            headings={headings}
                                            data={state.data}
                                            pageSize={pageSize}
                                            onPageChange={handlePageChange}
                                            onClick={handleRowClick}
                                            key='myTournaments'
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
                                                    <i><p>No tournaments found.</p>
                                                    <p>Try creating a new one!</p></i>
                                                </Col>
                                            </Row>
                                                       
                                        </Col>
                                    </div>                
                                </Container>
                                } 
                            </Row>                                                     
                        </Card.Body>
                    </Card>                    
                </Container>
            </Col>
            <OptionsPopUp show={state.show} selected={state.selected} handleClose={handleHide}/>  
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
        case 'setSelected' : return{
            ...state,
            selected: action.value
        }
        default: throw new Error();
    }
}

export default MyTournaments;