import React, { useEffect, useState } from 'react'
import {
  CButton,
  CCard,
  CCardBody,
  CCardHeader,
  CCol,
  CForm,
  CFormInput,
  CFormLabel,
  CFormFeedback,
  CRow,
  CTableRow,
  CFormSelect,
  CTable,
  CTableBody,
  CTableDataCell,
  CTableHeaderCell,
  CTableHead,
} from '@coreui/react'
import Pagination from '../../UI/pagination/Pagination'
import Swal from 'sweetalert2'
import {
  getAllWithPagination,
  createOrUpdate,
  getModuleById,
  deleteModuleById,
} from '../../../service/module/ModuleService'
import { getAll as getAllAppScopes } from '../../../service/application-scope/ApplicationScopeService'

const Module = () => {
  // dynamic form fields
  const [formModules, setFormModules] = useState([{ id: 1 }])
  const [formModuleFields, setFormModuleFiedls] = useState([])

  // page response
  const [totalPages, setTotalPages] = useState()
  const [currentPage, setCurrentPage] = useState(0)
  const [modules, setModules] = useState([])
  const pageSize = 10

  const [editable, setEditable] = useState(false)
  const [validated, setValidated] = useState(false)
  const [applicationScopes, setApplicationScopes] = useState([])
  const [formData, setFormData] = useState({
    id: '',
    applicationScope: '-1',
    modules: [],
  })

  useEffect(() => {
    getAllScopse()
  }, [])

  useEffect(() => {
    getAllModules(currentPage, pageSize)
  }, [currentPage])

  // ( + ) => Adding module fields
  const handleAddFields = () => {
    setFormModules((prevFields) => {
      const lengthOfArr = prevFields.length
      const updatedFields = [...prevFields]
      const lastFieldId = updatedFields[lengthOfArr - 1].id + 1
      updatedFields[lengthOfArr] = { ...updatedFields[lengthOfArr], ['id']: lastFieldId }
      return updatedFields
    })
  }

  // ( - ) => Removing module fields
  const handleRemoveFields = (id) => {
    setFormModules(formModules.filter((field) => field.id !== id))
    const moduleArrayIndex = id - 1
    setFormModuleFiedls((prevFields) => {
      let updatedFields = [...prevFields]
      updatedFields = updatedFields.filter((_, index) => index !== moduleArrayIndex)
      return updatedFields
    })
  }

  // Setting array of modules
  const handleChangeModuleArray = (id, field, value) => {
    const moduleArrayIndex = id - 1
    setFormModuleFiedls((prevFields) => {
      const updatedObjects = [...prevFields]
      updatedObjects[moduleArrayIndex] = { ...updatedObjects[moduleArrayIndex], [field]: value }
      return updatedObjects
    })
  }

  const handleSubmit = async (event) => {
    const form = event.currentTarget
    if (form.checkValidity() === false) {
      event.preventDefault()
      event.stopPropagation()
      setValidated(true)
    } else {
      const updatedForm = { ...formData }
      updatedForm.key = formModuleFields[0].key
      updatedForm.module = formModuleFields[0].module
      updatedForm.status = formModuleFields[0].status
      updatedForm.modules = formModuleFields
      try {
        const data = await createOrUpdate(updatedForm)
        if (data.status === 'OK') {
          Swal.fire({
            icon: 'success',
            title: 'Success',
            text: data.message,
          })
          getAllModules(currentPage, pageSize)
          handleReset()
        } else {
          Swal.fire({
            icon: 'error',
            title: 'Error',
            text: data.message,
          })
        }
      } catch (error) {
        console.error('Error occuring while creating modules. ', error)
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: error.response.data.details[1],
        })
      }
    }
  }

  const getAllModules = async (currentPage, pageSize) => {
    try {
      const data = await getAllWithPagination(currentPage, pageSize)
      if (data.data.status == 'OK') {
        setModules(data.data.data.dataList)
        setTotalPages(data.data.data.totalPages)
        setCurrentPage(data.data.data.currentPage)
      } else {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: data.data.message,
        })
      }
    } catch (error) {
      console.error('Error occuring while calling user service to fetch all modules. ', error)
      Swal.fire({
        icon: 'error',
        title: 'Internal Server Error',
        text: 'Modules fetching failed.',
      })
    }
  }

  const getAllScopse = async () => {
    try {
      const data = await getAllAppScopes()
      if (data.status == 'OK') {
        setApplicationScopes(data.data)
      } else {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: data.message,
        })
      }
    } catch (error) {
      console.error('Error occuring while calling user service to fetch all scopes. ', error)
      Swal.fire({
        icon: 'error',
        title: 'Internal Server Error',
        text: 'Application scopes fetching failed.',
      })
    }
  }

  const handleFormChange = (e) => {
    const { id, value } = e.target
    setFormData((prevData) => ({
      ...prevData,
      [id]: value,
    }))
  }

  const handleEditModule = async (id) => {
    try {
      handleReset()
      const data = await getModuleById(id)
      if (data.data.status == 'OK') {
        const module = data.data.data
        setFormModuleFiedls((prevFields) => {
          const updatedObjects = [...prevFields]
          updatedObjects[0] = { ...updatedObjects[0], ['key']: module.key }
          updatedObjects[0] = { ...updatedObjects[0], ['module']: module.module }
          updatedObjects[0] = { ...updatedObjects[0], ['status']: module.status }
          return updatedObjects
        })
        setFormData((prevData) => ({
          ...prevData,
          id: module.id,
          applicationScope: module.uuid,
        }))
        setEditable(true)
      } else {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: data.data.message,
        })
      }
    } catch (error) {
      console.error('Error occuring while calling user service to fetch module. ', error)
      Swal.fire({
        icon: 'error',
        title: 'Internal Server Error',
        text: 'Module fetching failed.',
      })
    }
  }

  const handleReset = () => {
    setEditable(false)
    setFormModuleFiedls([])
    setFormModules([{ id: 1 }])
    setFormData((prevData) => ({
      ...prevData,
      id: '',
      applicationScope: '-1',
      modules: [],
    }))
  }

  const handleDelete = async (id) => {
    try {
      const result = await Swal.fire({
        title: 'Do you want to delete?',
        showDenyButton: true,
        showCancelButton: true,
        confirmButtonText: 'Delete',
        denyButtonText: `Don't delete`,
      })
      if (result.isConfirmed) {
        const data = await deleteModuleById(id)
        if (data.data.status === 'OK') {
          Swal.fire('Deleted!', '', 'success')
          getAllModules(currentPage, pageSize)
        } else {
          Swal.fire({
            icon: 'error',
            title: 'Error',
            text: data.data.message,
          })
        }
        handleReset()
      } else if (result.isDenied) {
        Swal.fire('Changes are not saved', '', 'info')
      }
    } catch (error) {
      console.error('Error occuring while calling user service to delete module. ', error)
      if (error.response.data.httpStatus === 'BAD_REQUEST') {
        Swal.fire({
          icon: 'error',
          title: 'Internal Server Error',
          text: error.response.data.message,
        })
      } else {
        Swal.fire({
          icon: 'error',
          title: 'Internal Server Error',
          text: 'Module deleting failed.',
        })
      }
    }
  }

  return (
    <CRow>
      <CCol xs={12}>
        <CCard className="mb-4">
          <CCardHeader>
            <strong>Manage Module</strong>
          </CCardHeader>
          <CCardBody>
            <CForm
              className="row gx-3 gy-2 align-items-center"
              noValidate
              validated={validated}
              onSubmit={handleSubmit}
            >
              <CCol sm={4}>
                <CFormLabel htmlFor="applicationScope">Application Scope</CFormLabel>
                <CFormSelect
                  value={formData.applicationScope}
                  id="applicationScope"
                  style={{ cursor: 'pointer' }}
                  required
                  onChange={handleFormChange}
                >
                  <option value="-1">Select an application scope</option>
                  {applicationScopes.map((scope) => (
                    <option key={scope.uniqueId} value={scope.uniqueId}>
                      {scope.scope}
                    </option>
                  ))}
                </CFormSelect>
                <CFormFeedback tooltip invalid>
                  Please select an scope.
                </CFormFeedback>
              </CCol>

              <div className="w-100" id="id" value={formData.id}></div>

              <React.Fragment>
                <CCol sm={3}>
                  <CFormLabel>Key Name</CFormLabel>
                </CCol>
                <CCol sm={3}>
                  <CFormLabel>Module Name</CFormLabel>
                </CCol>
                <CCol sm={3}>
                  <CFormLabel>Status</CFormLabel>
                </CCol>
                <CCol sm={1}></CCol>
              </React.Fragment>

              {formModules.map((field) => (
                <React.Fragment key={field.id}>
                  <CCol sm={3}>
                    <CFormInput
                      id={`key_${field.id}`}
                      value={formModuleFields[field.id - 1]?.key || ''}
                      placeholder="Key name"
                      onChange={(e) => {
                        handleChangeModuleArray(field.id, 'key', e.target.value)
                      }}
                      required
                    />
                    <CFormFeedback tooltip invalid>
                      Please provide a key name.
                    </CFormFeedback>
                  </CCol>
                  <CCol sm={3}>
                    <CFormInput
                      id={`module_${field.id}`}
                      value={formModuleFields[field.id - 1]?.module || ''}
                      placeholder="Module name"
                      onChange={(e) => {
                        handleChangeModuleArray(field.id, 'module', e.target.value)
                      }}
                      required
                    />
                    <CFormFeedback tooltip invalid>
                      Please provide a module name.
                    </CFormFeedback>
                  </CCol>
                  <CCol sm={3}>
                    <CFormSelect
                      value={formModuleFields[field.id - 1]?.status || '-1'}
                      id={`status_${field.id}`}
                      style={{ cursor: 'pointer' }}
                      onChange={(e) => {
                        handleChangeModuleArray(field.id, 'status', e.target.value)
                      }}
                      required
                    >
                      <option value="-1">Select a status</option>
                      <option value="Active">Active</option>
                      <option value="In_active">In-active</option>
                    </CFormSelect>
                    <CFormFeedback tooltip invalid>
                      Please select a status.
                    </CFormFeedback>
                  </CCol>
                  <CCol sm={1}>
                    <CCol xs="auto" style={{ display: editable ? 'none' : 'block' }}>
                      <CButton
                        type="button"
                        className="btn btn-danger btn-sm"
                        onClick={() => handleRemoveFields(field.id)}
                      >
                        <strong style={{ color: 'white' }}>-</strong>
                      </CButton>
                    </CCol>
                  </CCol>
                </React.Fragment>
              ))}

              <CCol sm={1}>
                <CCol xs="auto" style={{ marginTop: '0px', display: editable ? 'none' : 'block' }}>
                  <CButton
                    type="button"
                    className="btn btn-success btn-sm"
                    onClick={handleAddFields}
                  >
                    <strong style={{ color: 'white' }}>+</strong>
                  </CButton>{' '}
                </CCol>
              </CCol>

              <div className="w-100"></div>
              <CRow className="justify-content-end">
                <CCol xs="auto" style={{ marginTop: '30px' }}>
                  <CButton color="primary" type="submit">
                    {(editable && 'Update') || 'Create'}
                  </CButton>{' '}
                  &nbsp;
                  <CButton
                    color="success"
                    type="reset"
                    style={{ color: 'white' }}
                    onClick={() => handleReset()}
                  >
                    Reset
                  </CButton>
                </CCol>
              </CRow>
            </CForm>
          </CCardBody>
        </CCard>
      </CCol>
      <CCol xs={12}>
        <CTable>
          <CTableHead color="dark">
            <CTableRow>
              <CTableHeaderCell scope="col">No</CTableHeaderCell>
              <CTableHeaderCell scope="col">Application Scope</CTableHeaderCell>
              <CTableHeaderCell scope="col">Module</CTableHeaderCell>
              <CTableHeaderCell scope="col">Key</CTableHeaderCell>
              <CTableHeaderCell scope="col">Status</CTableHeaderCell>
              <CTableHeaderCell scope="col">Action</CTableHeaderCell>
            </CTableRow>
          </CTableHead>
          <CTableBody>
            {modules.map((module, index) => (
              <React.Fragment key={module.id}>
                <CTableRow>
                  <CTableDataCell>{index + 1}</CTableDataCell>
                  <CTableDataCell>{module.applicationScope}</CTableDataCell>
                  <CTableDataCell>{module.module}</CTableDataCell>
                  <CTableDataCell>{module.key}</CTableDataCell>
                  <CTableDataCell>{module.status}</CTableDataCell>
                  <CTableDataCell>
                    <CButton
                      type="button"
                      className="btn btn-primary btn-sm"
                      onClick={() => handleEditModule(module.id)}
                    >
                      <span style={{ color: 'white' }}>Edit</span>
                    </CButton>{' '}
                    &nbsp;
                    <CButton
                      type="button"
                      className="btn btn-danger btn-sm"
                      onClick={() => handleDelete(module.id)}
                    >
                      <span style={{ color: 'white' }}>Delete</span>
                    </CButton>{' '}
                  </CTableDataCell>
                </CTableRow>
              </React.Fragment>
            ))}
          </CTableBody>
        </CTable>
        <Pagination
          currentPage={currentPage}
          totalPages={totalPages}
          onPageChange={setCurrentPage}
        />
      </CCol>
    </CRow>
  )
}
export default Module
