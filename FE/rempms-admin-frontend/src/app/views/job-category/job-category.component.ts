import {Component, OnInit} from '@angular/core';
import {JobCategory} from "../../model/jobCategory/JobCategory";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import Swal from "sweetalert2";
import {JobCategoryService} from "../../service/jobCategory/job-category.service";

@Component({
  selector: 'app-job-category',
  templateUrl: './job-category.component.html',
  styleUrls: ['./job-category.component.scss']
})
export class JobCategoryComponent implements OnInit {

  jobCategoryForm: FormGroup | any;
  jobCategory: JobCategory | any;
  jobCategories: JobCategory[] | any;

  constructor(private formBuilder: FormBuilder,
              private jobCategoryService: JobCategoryService) {
  }

  ngOnInit(): void {
    this.getAllActiveJobCategories();
    this.jobCategoryForm = this.formBuilder.group({
      idJobCategory: [''],
      jobCategoryName: ['', Validators.required],
      jobCategoryDescription: ['', Validators.required],
      commonStatus: ['-1', Validators.required]
    });
  }

  saveUpdateJobCategory() {
    if (this.jobCategoryForm.valid) {
      const jobCategoryFormValues = this.jobCategoryForm.value;
      this.jobCategoryService.saveUpdateJobCategory(jobCategoryFormValues).subscribe(
        (data) => {
          if (data.status == "OK") {
            this.resetJobCategoryForm();
            Swal.fire("Saved!", data.message, "success");
          } else {
            this.resetJobCategoryForm();
            Swal.fire("Cancelled", data.message, "error");
          }
        },
        (error) => {
          console.log("saveUpdateJobCategory() >> " + error.get);
        });

    } else {
      this.jobCategoryForm.markAllAsTouched();
    }
  }

  getActiveJobCategoryById(idJobCategory: string) {
    this.jobCategoryService.getActiveJobCategoryById(idJobCategory).subscribe(
      (data) => {
        if (data.data != null) {
          this.jobCategory = data.data;
          //Patch values to the form
          this.jobCategoryForm.patchValue({
            idJobCategory: this.jobCategory.idJobCategory,
            jobCategoryName: this.jobCategory.jobCategoryName,
            jobCategoryDescription: this.jobCategory.jobCategoryDescription,
            commonStatus: this.jobCategory.commonStatus
          });
        }
      },
      (error) => {
        console.log("getActiveJobCategoryById() >> " + error.get);
      });
  }

  private getAllActiveJobCategories() {
    this.jobCategoryService.getAllActiveJobCategories().subscribe(
      (data) => {
        if (data.data != null) {
          this.jobCategories = data.data;
        }
      }, (error) => {
        console.log("getAllActiveJobCategories() >> " + error.get);
      });
  }

  deleteJobCategoryById(idJobCategory: string) {
    this.jobCategoryService.deleteJobCategoryById(idJobCategory).subscribe(
      (data) => {
        if (data.status == "OK") {
          this.resetJobCategoryForm();
          Swal.fire("Deleted!", data.message, "success");
        } else {
          this.resetJobCategoryForm();
          Swal.fire("Cancelled", data.message, "error");
        }
      }, (error) => {
        console.log("deleteJobCategoryById() >> " + error.get);
      });
  }

  resetJobCategoryForm() {
    this.getAllActiveJobCategories();
    this.jobCategoryForm.reset();
    this.jobCategoryForm.get('commonStatus')?.patchValue('-1');
  }
}
